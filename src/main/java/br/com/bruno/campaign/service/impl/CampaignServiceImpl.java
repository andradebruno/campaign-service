package br.com.bruno.campaign.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.repository.CampaignRepository;
import br.com.bruno.campaign.service.CampaignService;

/**
 * Serviço utilizado para o CRUD da aplicação.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
@Service
@Validated
public class CampaignServiceImpl implements CampaignService {

	private static final Logger log = LoggerFactory.getLogger(CampaignService.class);

	@Autowired
	private Topic topic;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public Campaign createCampaign(String campaignName, String teamId, LocalDate campaignStartDate,
			LocalDate campaignEndDate) {

		List<Campaign> activeCampaigns = campaignRepository.findAllActiveCampaignsBetweenDates(campaignStartDate,
				campaignEndDate, Sort.by(Sort.Direction.ASC, "campaignEndDate"));

		activeCampaigns.forEach(campaign -> {
			campaign.setCampaignEndDate(campaign.getCampaignEndDate().plusDays(1));
			addDayToCampaignEndDate(campaign, activeCampaigns);
			campaignRepository.save(campaign);
			// jmsTemplate.convertAndSend(topic, campaign);
			log.info("Postando mensagem: {}", campaign.toString());
		});

		// campaignRepository.saveAll(activeCampaigns);
		return campaignRepository.save(new Campaign(campaignName, teamId, campaignStartDate, campaignEndDate));
	}

	@Override
	public void updateCampaign(Campaign campaign) {
		campaignRepository.save(campaign);
		jmsTemplate.convertAndSend(topic, campaign);
		log.info("Postando mensagem: {}", campaign.toString());

	}

	@Override
	public List<Campaign> findActiveCampaigns(LocalDate now) {
		return campaignRepository.findAllActivesCampaigns(LocalDate.now(),
				Sort.by(Sort.Direction.ASC, "campaignEndDate"));
	}

	@Override
	public List<Campaign> findActiveCampaignsBetweenDates(LocalDate campaignStartDate, LocalDate campaignEndDate) {
		return campaignRepository.findAllActiveCampaignsBetweenDates(campaignStartDate, campaignEndDate,
				Sort.by(Sort.Direction.ASC, "campaignEndDate"));
	}

	@Override
	public List<Campaign> findActiveCampaignsByTeamId(String teamId) {
		return campaignRepository.findAllByTeamIdAndCampaignStartDateLessThanEqualAndCampaignEndDateGreaterThanEqual(
				teamId, LocalDate.now(), LocalDate.now());
	}

	@Override
	public Optional<Campaign> findByCampaignId(String campaignId) {
		return campaignRepository.findById(campaignId);
	}

	@Override
	public void deleteCampignById(String campaignId) {
		campaignRepository.deleteById(campaignId);
	}

	@Override
	public void saveAllCampaigns(List<Campaign> campaignList) {
		campaignRepository.saveAll(campaignList);
	}

	/**
	 * Método auxliar para adição de dias.
	 *
	 * @param campaign
	 * @param activeCampaigns
	 */
	private void addDayToCampaignEndDate(Campaign campaign, List<Campaign> activeCampaigns) {

		activeCampaigns.forEach(activeCampaign -> {
			if (!activeCampaign.equals(campaign)
					&& activeCampaign.getCampaignEndDate().isEqual(campaign.getCampaignEndDate())) {
				campaign.setCampaignEndDate(campaign.getCampaignEndDate().plusDays(1));
			}
		});

	}
}

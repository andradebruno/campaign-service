package br.com.bruno.campaign.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.repository.CampaignRepository;
import br.com.bruno.campaign.utils.CampaignGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignServiceImplTest {

	@Autowired
	private CampaignService campaignService;
	@Autowired
	private CampaignRepository campaignRepository;

	@Before
	public void setUp() {
		campaignRepository.deleteAll();
		campaignRepository.saveAll(CampaignGenerator.getCampaigns());
	}

	/**
	 * Teste de verificação das datas de encerramento das campanhas foram
	 * atualizados com sucesso.
	 */

	@Test
	public void testToCreateCampaignWithCorrectEndDates() {
		Campaign campaign = campaignService.createCampaign("Campaign 3", "Team-03", LocalDate.of(2017, 10, 01),
				LocalDate.of(2017, 10, 03));

		assertThat(campaign).as("Cadastro da campanha 3, verificando os dados de acordo com o template.").isNotNull()
				.extracting("campaignName", "teamId", "campaignStartDate", "campaignEndDate")
				.contains("Campaign 3", "Team-03", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 03));

		assertThat(campaignRepository.findAll())
				.as("Atualizacao dos dados das campanhas 1 e 2 conforme regras definidas no documento")
				.extracting("campaignName", "teamId", "campaignStartDate", "campaignEndDate")
				.contains(tuple("Campaign 1", "Team-01", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 05)),
						tuple("Campaign 2", "Team-02", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 04)),
						tuple("Campaign 3", "Team-03", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 03)));

	}

	/**
	 * Teste da Busca de campanhas ativas entre datas.
	 */
	@Test
	public void testToFindActiveCampaignsBetweenDates() {
		assertThat(campaignService.findActiveCampaignsBetweenDates(LocalDate.of(2017, 10, 01),
				LocalDate.of(2017, 10, 02))).as(
						"Deve trazersomente uma Campanha (Campanha 2) dado que somente ela esta dentro da vigência deste período")
						.hasSize(1);

		assertThat(campaignService.findActiveCampaignsBetweenDates(LocalDate.of(2017, 10, 01),
				LocalDate.of(2017, 10, 03))).as(
						"Deve trazer as duas Campanhas ativas dado que todas estão com a vigência dentro deste período")
						.hasSize(2);

		assertThat(campaignService.findActiveCampaignsBetweenDates(LocalDate.of(2017, 10, 01),
				LocalDate.of(2017, 10, 04))).as(
						"Deve trazer as duas Campanhas ativas dado que todas estão com a vigência dentro deste período")
						.hasSize(2);
	}

}

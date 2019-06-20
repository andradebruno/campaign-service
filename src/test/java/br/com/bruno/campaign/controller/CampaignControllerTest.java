package br.com.bruno.campaign.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.repository.CampaignRepository;
import br.com.bruno.campaign.utils.CampaignGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignControllerTest {

	@Autowired
	private CampaignController campaignController;

	@Autowired
	private CampaignRepository campaignRepository;

	@Mock
	private HttpServletRequest request;

	@Before
	public void setUp() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		campaignRepository.deleteAll();
		campaignRepository.saveAll(CampaignGenerator.getCampaigns());
	}

	/**
	 * Teste de criação de campanha.
	 */
	@Test
	public void testCreateCampaign() {
		ResponseEntity responseEntity = campaignController.createCampaign(CampaignGenerator.createCampaignRequest());
		assertThat(responseEntity.getStatusCode()).as("O Status code deve ser 201").isEqualTo(HttpStatus.CREATED);

	}

	/**
	 * Teste de atualização de campanha.
	 */
	@Test
	public void testUpdateCampaign() {
		Campaign campaign = campaignRepository.findAll().stream().findFirst().get();
		final ResponseEntity<?> responseEntity = campaignController.updateCampaign(campaign.getCampaignId(),
				CampaignGenerator.createCampaignRequest());
		assertThat(responseEntity.getStatusCode()).as("O status code deve ser 204").isEqualTo(HttpStatus.NO_CONTENT);
	}

	/**
	 * Teste que busca as campanhas ativas.
	 */
	@Test
	public void testToFindActiveCampaigns() {
		campaignRepository.saveAll(CampaignGenerator.getActiveCampaigns());
		ResponseEntity<List<Campaign>> activeCampaigns = campaignController.findAllActiveCampaigns();
		System.out.println(activeCampaigns.getBody());
		assertThat(activeCampaigns.getBody())
				.as("Deve retornar duas campanhas (Campanha 31 e 32), pois somente essas ainda estão ativas").hasSize(2)
				.extracting("campaignName", "teamId", "campaignStartDate", "campaignEndDate")
				.contains(tuple("Campaign 31", "Team-01", LocalDate.now(), LocalDate.now().plusDays(1)),
						tuple("Campaign 32", "Team-01", LocalDate.now(), LocalDate.now().plusDays(2)));

	}

	/**
	 * Teste de busca de campanha por ID.
	 */
	@Test
	public void testFindCampaignById() {
		Campaign campaign = campaignRepository.findAll().stream().findFirst().get();
		ResponseEntity responseEntity = campaignController.findByCampaignId(campaign.getCampaignId());
		assertThat(responseEntity.getStatusCode()).as("O Status code deve ser 200").isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull().hasNoNullFieldsOrProperties();
	}

	/**
	 * Teste de busca de campanha ativa de acordo com o time.
	 */
	@Test
	public void testFindCampaignByTeam() {
		campaignRepository.saveAll(CampaignGenerator.getActiveCampaigns());
		ResponseEntity<List<Campaign>> responseEntity = campaignController.findCampaignByTeam("Team-01");
		assertThat(responseEntity.getStatusCode()).as("O Status code deve ser 200").isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).hasSize(2);
	}

	/**
	 * Teste de deleção de campanha por ID.
	 */
	@Test
	public void testDeleteById() {
		Campaign campaign = campaignRepository.findAll().stream().findAny().get();
		final ResponseEntity<?> responseEntity = campaignController.deleteByCampaignId(campaign.getCampaignId());
		assertThat(responseEntity.getStatusCode()).as("O Status code deve ser NO_CONTENT")
				.isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(campaignRepository.findById(campaign.getCampaignId())).as("A campanha tem que ser deletada")
				.isEmpty();
	}

}

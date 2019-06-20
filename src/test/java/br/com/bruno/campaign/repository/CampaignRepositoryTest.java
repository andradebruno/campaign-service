package br.com.bruno.campaign.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.campaign.utils.CampaignGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignRepositoryTest {

	@Autowired
	private CampaignRepository campaignRepository;

	@Before
	public void setUp() {
		campaignRepository.deleteAll();
	}

	/**
	 * Teste para verificar se não vai trazer campanhas inativas por engano.
	 */
	@Test
	public void testToNotFindAnyActiveCampaign() {
		campaignRepository.saveAll(CampaignGenerator.getCampaigns());
		assertThat(campaignRepository.findAllActivesCampaigns(LocalDate.now().plusDays(3),
				Sort.by(Sort.Direction.ASC, "campaignEndDate"))).as("Deve Trazer nenhuma campanha.").isNullOrEmpty();

		assertThat(campaignRepository.findAllActivesCampaigns(LocalDate.of(2017, 10, 05),
				Sort.by(Sort.Direction.ASC, "campaignEndDate"))).as("Deve Trazer nenhuma campanha.").isNullOrEmpty();

	}

}

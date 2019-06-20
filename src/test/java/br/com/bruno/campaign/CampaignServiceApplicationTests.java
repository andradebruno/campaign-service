package br.com.bruno.campaign;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bruno.campaign.repository.CampaignRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignServiceApplicationTests {

	@Autowired
	private CampaignRepository campaignRepository;

	@Test
	public void contextLoads() {
		assertThat(campaignRepository.findAll()).as(
				"CampaignServiceImplTest para verificar se os dados do json campaign.json foram carregados corretamente no MongoDB")
				.isNotNull().isNotEmpty().as("Devem existir dois registros no banco").hasSize(2)
				.as("Campos devem ser preenchidos e os dados devem estar corretos")
				.extracting("campaignName", "teamId", "campaignStartDate", "campaignEndDate")
				.contains(tuple("Campaign 1", "Team-01", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 03)),
						tuple("Campaign 2", "Team-02", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 02)));
	}

}

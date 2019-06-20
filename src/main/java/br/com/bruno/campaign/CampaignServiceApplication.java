package br.com.bruno.campaign;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.service.CampaignService;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.com.bruno.campaign.repository")
@EnableFeignClients
@EnableHystrix
@EnableAutoConfiguration
@EnableJms
public class CampaignServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignServiceApplication.class, args);
	}

	@Bean
	public Topic topic() {
		return new ActiveMQTopic("campaign-topic-notification");
	}

	/**
	 * Loader de campanhas presente no arquivo resources/campaign.json
	 *
	 * @param campaignService
	 * @return
	 */
	@Bean
	CommandLineRunner runner(CampaignService campaignService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Campaign>> typeReference = new TypeReference<List<Campaign>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/campaign.json");
			try {
				List<Campaign> campaignList = mapper.readValue(inputStream, typeReference);
				campaignService.saveAllCampaigns(campaignList);
				System.out.println("Campaigns Saved!");
			} catch (IOException e) {
				System.out.println("Unable to save campaigns: " + e.getMessage());
			}
		};
	}

}

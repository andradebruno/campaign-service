package br.com.bruno.campaign.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.response.CampaignResponse;

/**
 * Classe auxiliar para criação de campanhas.
 */
public class CampaignGenerator {

	public static List<Campaign> getCampaigns() {
		List<Campaign> campaignList = new ArrayList();
		campaignList.add(new Campaign("Campaign 1", "Team-01", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 03)));

		campaignList.add(new Campaign("Campaign 2", "Team-02", LocalDate.of(2017, 10, 01), LocalDate.of(2017, 10, 02)));
		return campaignList;
	}

	public static List<Campaign> getActiveCampaigns() {
		List<Campaign> campaignList = new ArrayList();
		campaignList.add(new Campaign("Campaign 31", "Team-01", LocalDate.now(), LocalDate.now().plusDays(1)));

		campaignList.add(new Campaign("Campaign 32", "Team-01", LocalDate.now(), LocalDate.now().plusDays(2)));
		return campaignList;
	}

	public static CampaignResponse createCampaignRequest() {
		return new CampaignResponse("Campaign 4", "Team-04", LocalDate.now(), LocalDate.now().plusDays(10));

	}

	public static CampaignResponse criarCampanhaComFalhasResource() {
		return new CampaignResponse(null, "TIME-1004", LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 20));

	}

}

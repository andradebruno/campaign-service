package br.com.bruno.campaign.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.bruno.campaign.domain.Campaign;

/**
 * Interface do serviço utilizado para o CRUD da aplicação.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
public interface CampaignService {

	/**
	 * Método de atualização de uma determinada campanha.
	 *
	 * @param campaign
	 */
	void updateCampaign(Campaign campaign);

	/**
	 * Método de criação de campanhas.
	 *
	 * @param campaignName
	 * @param teamId
	 * @param campaignStartDate
	 * @param campaignEndDate
	 * @return
	 */
	Campaign createCampaign(@NotNull String campaignName, @NotNull String teamId, @NotNull LocalDate campaignStartDate,
			@NotNull LocalDate campaignEndDate);

	/**
	 * Método de busca de todas as campanhas ativas, considerando a data atual como
	 * data de corte.
	 *
	 * @param now
	 * @return
	 */
	List<Campaign> findActiveCampaigns(LocalDate now);

	/**
	 * Método de busca de todas as campanhas ativas entre duas datas.
	 *
	 * @param campaignStartDate
	 * @param campaignEndDate
	 * @return
	 */
	List<Campaign> findActiveCampaignsBetweenDates(LocalDate campaignStartDate, LocalDate campaignEndDate);

	/**
	 * Método de busca todas as campanhas ativas para determinado time.
	 *
	 * @param teamId
	 * @return
	 */
	List<Campaign> findActiveCampaignsByTeamId(String teamId);

	/**
	 * Método de busca de uma determinada campanha de acordo com seu id.
	 *
	 * @param campaignId
	 * @return
	 */
	Optional<Campaign> findByCampaignId(String campaignId);

	/**
	 * Método de deleção de campanha considerando o id.
	 *
	 * @param campaignId
	 */
	void deleteCampignById(String campaignId);

	/**
	 * Método para salvar várias campanhas de uma vez.
	 *
	 * @param campaignList
	 */
	void saveAllCampaigns(List<Campaign> campaignList);

}

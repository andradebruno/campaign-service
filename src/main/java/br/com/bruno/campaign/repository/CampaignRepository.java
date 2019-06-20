package br.com.bruno.campaign.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bruno.campaign.domain.Campaign;

/**
 * Interface utilizada para CRUD no MongoDB.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
@Repository
public interface CampaignRepository extends MongoRepository<Campaign, String> {

	@Query("{ 'campaignStartDate' : { $lte: ?0 }, 'campaignEndDate' : { $gte: ?0 } }")
	List<Campaign> findAllActivesCampaigns(LocalDate now, Sort sort);

	@Query("{ 'campaignStartDate' : { $gte: ?0, $lte: ?1}, 'campaignEndDate' : { $gte: ?0, $lte: ?1 } }")
	List<Campaign> findAllActiveCampaignsBetweenDates(LocalDate campaignStartDate, LocalDate campaignEndDate,
			Sort sort);

	List<Campaign> findAllByTeamIdAndCampaignStartDateLessThanEqualAndCampaignEndDateGreaterThanEqual(String teamId,
			LocalDate campaignStartDate, LocalDate campaignEndDate);
}

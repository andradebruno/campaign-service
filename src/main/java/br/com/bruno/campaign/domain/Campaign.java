package br.com.bruno.campaign.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import br.com.bruno.campaign.response.CampaignResponse;

/**
 * Entidade utilizada para mapear os dados Java - MongoDB.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
@Document(collection = "campaign")
public class Campaign implements Serializable {

	public static final long serialVersionUID = 1L;

	@Id
	private String campaignId;

	@Indexed
	@NotNull(message = "É necessário um nome para a campanha!")
	@Field("campaign_name")
	private String campaignName;

	@Indexed
	@NotNull(message = "O id do time não pode ser vazio!")
	@Field("team_id")
	private String teamId;

	@Indexed
	@NotNull(message = "A data de inicio da campanha não pode ser vazia!")
	@Field("campaign_start_date")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate campaignStartDate;

	@Indexed
	@NotNull(message = "A data de fim da campanha não pode ser vazia!")
	@Field("campaign_end_date")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate campaignEndDate;

	public Campaign() {

	}

	public Campaign(String campaignName, String teamId, LocalDate campaignStartDate, LocalDate campaignEndDate) {
		this.campaignName = campaignName;
		this.teamId = teamId;
		this.campaignStartDate = campaignStartDate;
		this.campaignEndDate = campaignEndDate;
	}

	public void updateCampaignData(CampaignResponse campaignResponse) {
		setCampaignName(campaignResponse.getCampaignName());
		setTeamId(campaignResponse.getTeamId());
		setCampaignStartDate(campaignResponse.getCampaignStartDate());
		setCampaignEndDate(campaignResponse.getCampaignEndDate());
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public LocalDate getCampaignStartDate() {
		return campaignStartDate;
	}

	public void setCampaignStartDate(LocalDate campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}

	public LocalDate getCampaignEndDate() {
		return campaignEndDate;
	}

	public void setCampaignEndDate(LocalDate campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Campaign that = (Campaign) o;
		return campaignId.equals(that.campaignId) && campaignName.equals(that.campaignName)
				&& teamId.equals(that.teamId) && campaignStartDate.equals(that.campaignStartDate)
				&& campaignEndDate.equals(that.campaignEndDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(campaignName, teamId, campaignStartDate, campaignEndDate);
	}

	@Override
	public String toString() {
		return "Campaign{" + "campaignId=" + campaignId + ", campaignName='" + campaignName + '\'' + ", teamId="
				+ teamId + ", campaignStartDate=" + campaignStartDate + ", campaignEndDate=" + campaignEndDate + '}';
	}
}

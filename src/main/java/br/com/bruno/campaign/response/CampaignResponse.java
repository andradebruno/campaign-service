package br.com.bruno.campaign.response;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import br.com.bruno.campaign.domain.Campaign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe que representa os dados recebidos e enviados pela API.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
@ApiModel(value = "CampaignResponse", description = "Campaign API Data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignResponse {

	@JsonIgnore
	private String campaignId;

	@NotNull(message = "É necessário um nome para a campanha!")
	@ApiModelProperty(value = "Nome da campanha", dataType = "string", required = true)
	private String campaignName;

	@NotNull(message = "O id do time não pode ser vazio!")
	@ApiModelProperty(value = "Id do time", dataType = "String", required = true)
	private String teamId;

	@NotNull(message = "A data de inicio da campanha não pode ser vazia!")
	@ApiModelProperty(value = "Data de inicio da campanha", dataType = "date", required = true)
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate campaignStartDate;

	@NotNull(message = "A data de fim da campanha não pode ser vazia!")
	@ApiModelProperty(value = "Data de fim da campanha", dataType = "date", required = true)
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate campaignEndDate;

	public CampaignResponse() {
	}

	public CampaignResponse(String campaignName, String teamId, LocalDate campaignStartDate,
			LocalDate campaignEndDate) {
		this.campaignName = campaignName;
		this.teamId = teamId;
		this.campaignStartDate = campaignStartDate;
		this.campaignEndDate = campaignEndDate;
	}

	public CampaignResponse(Campaign campaign) {
		this.campaignId = campaign.getCampaignId();
		this.campaignName = campaign.getCampaignName();
		this.teamId = campaign.getTeamId();
		this.campaignStartDate = campaign.getCampaignStartDate();
		this.campaignEndDate = campaign.getCampaignEndDate();
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

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	@Override
	public String toString() {
		return "CampaignResponse{" + "campaignName='" + campaignName + '\'' + ", teamId=" + teamId
				+ ", campaignStartDate=" + campaignStartDate + ", campaignEndDate=" + campaignEndDate + '}';
	}
}

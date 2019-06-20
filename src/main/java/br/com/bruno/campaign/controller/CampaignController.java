package br.com.bruno.campaign.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.bruno.campaign.domain.Campaign;
import br.com.bruno.campaign.exception.CampaignNotFundException;
import br.com.bruno.campaign.response.CampaignResponse;
import br.com.bruno.campaign.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller para expor os métodos da aplicação.
 *
 * @author Bruno Andrade
 * @email andradedbruno@gmail.com
 */
@RestController
@RequestMapping("/v1/campaign")
@Api(value = "Campaign", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CampaignController {

	private static final Logger log = LoggerFactory.getLogger(CampaignController.class);

	@Autowired
	private CampaignService campaignService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cria uma nova campanha", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity createCampaign(@Valid @RequestBody CampaignResponse campaignResponse) {

		Campaign campaign = campaignService.createCampaign(campaignResponse.getCampaignName(),
				campaignResponse.getTeamId(), campaignResponse.getCampaignStartDate(),
				campaignResponse.getCampaignEndDate());

		log.info("Campanha: {} criada com sucesso", campaign.toString());
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{campaignId}")
				.buildAndExpand(campaign.getCampaignId()).toUri()).build();

	}

	@PutMapping(value = "/{campaignId}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Atualiza a campanha por id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<?> updateCampaign(@PathVariable String campaignId,
			@Valid @RequestBody CampaignResponse campaignResponse) {

		Optional<Campaign> campaignOptional = campaignService.findByCampaignId(campaignId);

		campaignOptional.ifPresent(campaign -> {
			campaign.updateCampaignData(campaignResponse);
			campaignService.updateCampaign(campaign);
			log.info("Campanha: {} atualizada com sucesso.", campaign.toString());
		});

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Busca todas as campanhas ativas, utilizando a data atual", response = CampaignResponse.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity findAllActiveCampaigns() {
		List<CampaignResponse> activeCampaigns = campaignService.findActiveCampaigns(LocalDate.now()).stream()
				.map(CampaignResponse::new).collect(Collectors.toList());

		return new ResponseEntity(activeCampaigns, HttpStatus.OK);
	}

	@GetMapping(value = "/{campaignId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Busca a campanha por id", response = CampaignResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<CampaignResponse> findByCampaignId(@PathVariable String campaignId) {
		Optional<Campaign> campaignOptional = campaignService.findByCampaignId(campaignId);

		if (campaignOptional.isPresent()) {
			CampaignResponse campaignResponse = new CampaignResponse(campaignOptional.get());
			return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
		}

		throw new CampaignNotFundException();
	}

	@GetMapping(value = "/team/{teamId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Busca campanha por id do time", response = CampaignResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity findCampaignByTeam(@PathVariable String teamId) {
		List<CampaignResponse> activeCampaignByTeam = campaignService.findActiveCampaignsByTeamId(teamId).stream()
				.map(CampaignResponse::new).collect(Collectors.toList());

		return new ResponseEntity<>(activeCampaignByTeam, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{campaignId}")
	@ApiOperation(value = "Deleta a campanha por id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No Content"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity deleteByCampaignId(@PathVariable String campaignId) {

		campaignService.deleteCampignById(campaignId);
		log.info("Deletando a campanha: {}.", campaignId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}

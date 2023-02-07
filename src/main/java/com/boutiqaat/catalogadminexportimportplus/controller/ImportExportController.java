package com.boutiqaat.catalogadminexportimportplus.controller;


import com.boutiqaat.catalogadminexportimportplus.kafka.ExportEvent;
import com.boutiqaat.catalogadminexportimportplus.kafka.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@NoArgsConstructor
@RequestMapping("/v1/catalog")
public class ImportExportController {

	@Autowired
	private Producer producer;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	@Qualifier(value ="redisTemplate")
	RedisTemplate<Object, Object> redisTemplate;

	@PostMapping(value = "/export",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> exportFileAsExcel() {
		try {
			    Map<String, Object> filters= new HashMap<>();
				ExportEvent exportEvent = new ExportEvent();
				exportEvent.setExportInfo("");
				exportEvent.setFilters(filters);
				//exportEvent.setUserId(CommonUtil.getLoggedInUserId());
				exportEvent.setPageId("");
				exportEvent.setStatus("Pending");
				exportEvent.setExportType("");
				producer.sendMessage(exportEvent);
			return new ResponseEntity<>(exportEvent, HttpStatus.OK);

		} catch(Exception e){
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not able to service", e);
			}

	}

	/*@GetMapping(value = "/export/list")
	public ResponseEntity<?> listOfTriggeredExports(@PageableDefault(page = 0, size = 20, sort = "id" ,
		direction = Sort.Direction.DESC) Pageable pages) {
		return new ResponseEntity<>(catalogProductFlatService.fetchUserExports(pages), HttpStatus.OK);
	}*/


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addFilterForDefaultStoreId(Map filtersStringMap) {
		if (!filtersStringMap.containsKey("store_id")) {
			filtersStringMap.put("store_id", 1);
		}
	}



}

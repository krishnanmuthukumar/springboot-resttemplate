package org.learning.springboot.spingtemplate.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class HackerankTestController {

	@GetMapping("/totalgoals")
	public int getTotalGoals(@RequestParam String team, @RequestParam String year) {
		String url1 = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team1=" + team;

		String url2 = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team2=" + team;

		JsonObject response1 = getResponsefromAPI(url1);
		int totalpages1 = response1.get("total_pages").getAsInt();

		JsonObject response2 = getResponsefromAPI(url2);
		int totalpages2 = response2.get("total_pages").getAsInt();

		int total = 0;

		for (int i = 1; i <= totalpages1; i++) {
			url1 = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team1=" + team + "&page="
					+ i;
			JsonObject body = getResponsefromAPI(url1);
			JsonArray result = body.get("data").getAsJsonArray();
			for (int j = 0; j < result.size(); j++) {
				JsonObject resultObject = result.get(j).getAsJsonObject();
				if (team.equalsIgnoreCase(resultObject.get("team1").getAsString())) {
					total += resultObject.get("team1goals").getAsInt();
				}
			}
		}

		for (int i = 1; i <= totalpages2; i++) {
			url2 = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team2=" + team + "&page="
					+ i;
			JsonObject body = getResponsefromAPI(url2);
			JsonArray result = body.get("data").getAsJsonArray();
			for (int j = 0; j < result.size(); j++) {
				JsonObject resultObject = result.get(j).getAsJsonObject();
				if (team.equalsIgnoreCase(resultObject.get("team2").getAsString())) {
					total += resultObject.get("team2goals").getAsInt();
				}
			}
		}

		System.out.println("total goals:: " + total);
		return total;

	}

	@GetMapping("totaldraws")
	public int getNumberOfDraws(@RequestParam String year) {

		int total = 0;
		for (int j = 1; j <= 11; j++) {

			String url1 = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&team2goals=" + j
					+ "&team1goals=" + j;
			JsonObject body = getResponsefromAPI(url1);
			total += body.get("total").getAsInt();

		}

		return total;

	}

	private JsonObject getResponsefromAPI(String url) {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response1 = restTemplate.getForEntity(url, String.class);
		Gson gson = new Gson();
		JsonObject body = gson.fromJson(response1.getBody(), JsonObject.class);

		return body;
	}

}

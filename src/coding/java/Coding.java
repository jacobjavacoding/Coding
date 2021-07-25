package coding.java;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Coding {
	/**
	 * read CSV string and providing the insights of the parameters like
	 * The number of unique customerId for each contractId.
     * The number of unique customerId for each geozone.	
     * The average buildduration for each geozone.
     * The list of unique customerId for each geozone.
	 * @param args -
	 */
	public static void main(String[] args) {
		String str = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\r\n" + "\r\n"
				+ "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\r\n" + "\r\n"
				+ "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\r\n" + "\r\n"
				+ "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\r\n" + "\r\n"
				+ "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

		String[] lines = str.split("\r\n");
		Map<String, List<String>> uniqueCustomerIdsforEachContractIdMap = new HashMap<String, List<String>>();
		Map<String, List<String>> uniqueCustomerIdsforEachGeozoneMap = new HashMap<String, List<String>>();
		Map<String, List<String>> buildDurationsforEachGeozoneMap = new HashMap<String, List<String>>();

		for (String s : lines) {
			if (s.length() > 0) {
				String[] eachLine = s.split(",");
				String customerId = eachLine[0];
				String contractId = eachLine[1];
				String geozone = eachLine[2];
				// String teamcode = eachLine[3];
				// String projectcode = eachLine[4];
				String buildduration = eachLine[5];
				uniqueCustomerIdsforEachContractIdMap = getUniqueIds(uniqueCustomerIdsforEachContractIdMap, customerId,
						contractId);
				uniqueCustomerIdsforEachGeozoneMap = getUniqueIds(uniqueCustomerIdsforEachGeozoneMap, customerId,
						geozone);
				buildDurationsforEachGeozoneMap = getUniqueIds(buildDurationsforEachGeozoneMap, buildduration, geozone);

			}
		}

		System.out.println(":::The number of unique customerIds for each contractId:::\n");
		for (String contractId : uniqueCustomerIdsforEachContractIdMap.keySet()) {
			List<String> list = uniqueCustomerIdsforEachContractIdMap.get(contractId);
			if (list != null) {
				System.out.println("for contractId " + contractId + " number of unique customerIds " + list.size());
			}
		}
		System.out.println("\n:::The number of unique customerIds for each geozone:::\n");
		for (String geozone : uniqueCustomerIdsforEachGeozoneMap.keySet()) {
			List<String> list = uniqueCustomerIdsforEachGeozoneMap.get(geozone);
			if (list != null) {
				System.out.println("for geozone " + geozone + " number of unique customerIds " + list.size());
			}
		}

		System.out.println("\n:::------average-build durations in seconds-------:::\n");
		Map<String, Double> averageBuildDurationsMap = new HashMap<String, Double>();
		for (String geozone : buildDurationsforEachGeozoneMap.keySet()) {
			List<Integer> buildDurationsList = new ArrayList<Integer>();
			List<String> buildDurationsforEachZoneList = buildDurationsforEachGeozoneMap.get(geozone);

			for (String buildDuration : buildDurationsforEachZoneList) {
				Integer buildDurationinInt = Integer.parseInt(buildDuration.substring(0, buildDuration.length() - 1));
				buildDurationsList.add(buildDurationinInt);
			}

			Double average = buildDurationsList.stream().mapToDouble(val -> val).average().orElse(0.0);
			averageBuildDurationsMap.put(geozone, average);
			System.out.println(geozone + "--" + averageBuildDurationsMap.get(geozone));
		}

		System.out.println("\n:::The list of unique customerIds for each geozone:::\n");
		for (String geozone : uniqueCustomerIdsforEachGeozoneMap.keySet()) {
			System.out.println("for geozone " + geozone + ": list of unique customerIds "
					+ uniqueCustomerIdsforEachGeozoneMap.get(geozone));
		}
	}

	/**
	 * method to get the list of unique ids (param1) for the given key or param (param2)
	 * @param map
	 * @param param1
	 * @param param2
	 * @return
	 */
	private static Map<String, List<String>> getUniqueIds(Map<String, List<String>> map, String param1, String param2) {
		List<String> list = new ArrayList<String>();
		if (map.isEmpty()) {
			list.add(param1);
			map.put(param2, list);
		} else {
			Set<String> set = map.keySet();
			if (set.contains(param2)) {
				List<String> tempList = map.get(param2);
				tempList.add(param1);
				map.put(param2, tempList);
			} else {
				list = new ArrayList<String>();
				list.add(param1);
				map.put(param2, list);
			}
		}
		return map;
	}

}

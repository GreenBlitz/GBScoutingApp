package com.example.util;

public class Constants {

	public static class Networking {
		public static final String serverURL = "http://109.186.131.164:5000/";
		// the absolute IP of the server after port forwarding
		// this is not final since we did not get an open port for our permanent server
	}

	public static class ScoutingPrompt {
		public static final int vibrationTime = 35; //milliseconds
	}

	public static class GamesPage {
		public static final int ALLIANCES = 2; // amount of alliances in a game (idk what FIRST gonna do next year)
		public static final int TEAMS_PER_ALLIANCE = 3; // amount of teams (robots) in each alliance
	}
}

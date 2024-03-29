package com.springboot.rest.webservices.socialmediaapp.constants;

public class ApiRoutes {

	public static final String LOCAL_HOST = "localhost";

	private static final String versionPrefix = "/app/v1";

	public static class Auth {

		private static final String prefix = versionPrefix + "/auth";
		public static final String REGISTER = prefix + "/register";
		public static final String LOGIN = prefix + "/login";

	}

	public static class User {

		private static final String prefix = versionPrefix + "/users";
		public static final String GET_ALL = prefix;
		public static final String GET_BY_ID = prefix + "/{id}";

	}

	public static class Post {

		private static final String prefix = versionPrefix + "/posts";
		public static final String GET_ALL = prefix;
		public static final String CREATE = prefix;
		public static final String GET_BY_ID = prefix + "/{id}";
		public static final String DELETE_BY_ID = prefix + "/{id}";
		public static final String GET_BY_USERID = versionPrefix+ "/users/{userId}"+"/posts";

	}

	public static class Comment {

		public static final String CREATE = versionPrefix + "/posts/{postId}/comments";
		public static final String GET_BY_POST = versionPrefix + "/posts/{postId}/comments";
		public static final String GET_BY_ID = versionPrefix + "/comments/{commentId}";
		public static final String GET_BY_USERID = versionPrefix + "/users/{userId}/comments";
	}

 }

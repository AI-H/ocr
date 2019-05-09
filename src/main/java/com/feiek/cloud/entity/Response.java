package com.feiek.cloud.entity;

import java.io.Serializable;

/**
 * @author 飞客不去
 * @date  创建时间 2019/5/8 10:41
 * @version 1.0
 */
public class Response<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7374060329101271860L;

	public enum Status {
		FAILED, SUCCESS
	}

	private Status status;
	private String message;
	/**
	 * 响应码：0标示成功
	 */
	private Integer statusCode;
	private T entity;

	public Response() {
	}

	public Response(Status status, String message, T entity) {
		this.status = status;
		this.message = message;
		this.entity = entity;
	}

	public Response(Status status, String message, T entity, Integer statusCode) {
		super();
		this.status = status;
		this.message = message;
		this.entity = entity;
		this.statusCode = statusCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public static <T> Response<T> success(T entity) {
		return new Response<T>(Status.SUCCESS, null, entity,0);
	}
	public static <T> Response<T> success(T entity,String message) {
		return new Response<T>(Status.SUCCESS, message, entity,0);
	}

	public static <T> Response<T> failed(String message) {
		return new Response<T>(Status.FAILED, message, null);
	}

	public static <T> Response<T> failed(String message, Integer statusCode) {
		return new Response<T>(Status.FAILED, message, null, statusCode);
	}

	public static <T> Response<T> failed(String message, T entity) {
		return new Response<T>(Status.FAILED, message, entity, 0);
	}

	@Override
	public String toString() {
		return "Response{" +
				"status=" + status +
				", message='" + message + '\'' +
				", statusCode=" + statusCode +
				", entity=" + entity +
				'}';
	}
}

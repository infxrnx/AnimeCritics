package com.project.anime.aop.exception;

public class ExternalApiFailed extends RuntimeException {
  public ExternalApiFailed(String message) {
    super(message);
  }
}

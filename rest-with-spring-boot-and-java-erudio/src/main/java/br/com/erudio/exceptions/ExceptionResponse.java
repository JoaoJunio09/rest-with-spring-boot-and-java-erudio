package br.com.erudio.exceptions;

import java.util.Date;

public record ExceptionResonse(Date timestamp, String message, String details) {
}

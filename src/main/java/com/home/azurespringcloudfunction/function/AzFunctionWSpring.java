package com.home.azurespringcloudfunction.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.stereotype.Component;

@Component
public class AzFunctionWSpring {
	@Autowired
	private Function<String, String> uppercase;

	@Autowired
	private FunctionCatalog functionCatalog;

	@FunctionName("bean")
	public HttpResponseMessage plainBeans(
			@HttpTrigger(name = "req", methods = { HttpMethod.GET,
					HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
			ExecutionContext context) {

		// Use plain Spring Beans.
		return request.createResponseBuilder(HttpStatus.OK)
				.body(uppercase.apply(request.getBody().orElse("Hello World")))
				.header("Content-Type", "application/json")
				.build();
	}

	@FunctionName("scf")
	public String springCloudFunction(
			@HttpTrigger(name = "req", methods = { HttpMethod.GET,
					HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
			ExecutionContext context) {

		// Use SCF composition.
		Function composed = this.functionCatalog.lookup("reverse|uppercase");

		return (String) composed.apply(request.getBody().orElse("Hello World"));
	}

}

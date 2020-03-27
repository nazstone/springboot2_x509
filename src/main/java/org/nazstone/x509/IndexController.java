package org.nazstone.x509;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@GetMapping
	public String tada() {
		return "tada";
	}
}

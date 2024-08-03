ROOT_DIR=$(shell pwd)

.PHONY: lint
lint:
	$(ROOT_DIR)/gradlew ktlintCheck

.PHONY: format
format:
	$(ROOT_DIR)/gradlew ktlintFormat
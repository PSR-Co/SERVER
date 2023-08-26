import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.jpa") version "1.8.21"
	kotlin("plugin.allopen") version "1.6.21"
	kotlin("plugin.noarg") version "1.6.21"
	kotlin("kapt") version "1.7.10"
	idea
}

group = "com.psr"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("mysql:mysql-connector-java:8.0.32")

	// jasypt 암호화
	implementation ("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

	// spring security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// jwt
	implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.2")

	//log
	implementation ("io.github.microutils:kotlin-logging:3.0.5")

	// valid
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// queryDSL
	implementation("com.infobip:infobip-spring-data-jpa-querydsl-boot-starter:8.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

	// redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// webclient
	implementation ("org.springframework.boot:spring-boot-starter-webflux")

	// random
	implementation("org.apache.commons:commons-lang3:3.12.0")

	// notification
	implementation("com.google.firebase:firebase-admin:9.2.0")
	implementation("com.squareup.okhttp3:okhttp:4.10.0")
}


allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

idea {
	module {
		val kaptMain = file("build/generated/source/kapt/main")
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}

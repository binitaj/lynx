# Secrets Management for Small IT Businesses
## Background 

Software startups and small IT businesses often face significant challenges in securely managing confidential data, such as Personally Identifiable Information (PII), credentials, and other sensitive information. Many companies frequently encounter data breaches. This issue is prevalent across the industry, impacting small IT companies and large enterprises alike. Numerous incidents arise from inadequate practices in storing and managing secrets and sensitive information. The increasing regulatory requirements exacerbate the problem.

## Compliance Requirements 

Compliance with standards such as SOC 2 Type 2 for storing sensitive customer information, HIPAA for patient information, GDPR and CCPA for PII, and COPPA for children’s online activities is essential for data protection. Initially, this project aimed to provide comprehensive compliance support. Later, focus was placed on SOC 2 Type 2 compliance, which outlines guidelines for businesses handling sensitive customer data. Small IT companies providing services to individuals must customize their solutions or configure customer interactions. Such companies must store customers’ PII data for identification and personalization, necessitating compliance with SOC 2 Type 2.

## Goal of this Project

The ultimate goal of this project is to develop a comprehensive solution that enables small businesses to achieve the required compliance without incurring prohibitive costs or dealing with cumbersome processes. 

## About the Project Name
To Native Americans, this creature was a keeper of secrets and occult knowledge; a powerful and silent animal, they said it had the ability to unravel mysteries. [1] 

## Solution Statement:  
A secure system outlined below uses cryptographic techniques tailored to the needs of small businesses, provides easy integration and installation options, and enables compliance. 

### Easy installation:
The system allows one-click or one-command installation. A single action from the user will set up the application and run-time service. The system will perform self-checks and start the encryption engine and API endpoints.    
### Ease of integration:
The solution will leverage mutual TLS (transport layer security) based on connections to secure, authenticate, and authorize the traffic. It provides simple integration options, such as REST endpoints over HTTPS. A sample client is provided to demonstrate the simplicity of integration. 

![diagram1](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram1.png)


## Architecture in a Nutshell
The architecture included the following components:
1.	A server that exposes REST endpoints to store and retrieve secrets for each client. The server enforces mutual TLS for REST communication.
2.	A sample client application that demonstrates the store and retrieve functionality with mutual TLS

![Architecture in a Nutshell](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram2.png)

## Technical Architecture of the Solution
Expanding on the previous architecture, let us further detail the technical architecture of the solution. The technology choices made were:
1.	A Java-based application, built with Spring Boot
2.	Use of Java Cryptography Engine for encryption, decryption, hashing, and other cryptographic routines
3.	Use of Java Key Storage for storing secrets
4.	Embedded Redis to store secrets, optionally writing them to disk.
5.	Integrate with SmallStep-CA for PKI infrastructure.
With these technology choices, the depiction of technical architecture follows.

![Technical Architecture](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram3.png)

## References

[1] ![D. J. Conway, _Animal Magick: The Art of Recognizing & Working with Familiars_. Llewellyn Publications, 1995.](https://www.google.com/books/edition/Animal_Magick/WeX82Hhy2cQC?hl=en&gbpv=1&pg=PA82&printsec=frontcover)

[2] 	P. Webb and M. Halbritter, _SSL:: Spring Boot_, 20 Mar 2024. [Online]. Available: https://docs.spring.io/spring-boot/reference/features/ssl.html.

[3] 	M. Stepankin, _mTLS: When certificate authentication is done wrong_, 17 Aug 2023. [Online]. Available: https://github.blog/security/vulnerability-research/mtls-when-certificate-authentication-is-done-wrong/.

[4] 	Sharifi, Hamid Reza; Martin, Greg, _TLS Setup in Spring_, 20 April 2024. [Online]. Available: https://www.baeldung.com/spring-tls-setup.

[5] 	_The keytool Command_, 10 October 2024. [Online]. Available: https://docs.oracle.com/en/java/javase/21/docs//specs/man/keytool.html.

[6] 	Internet Security Research Group, _Certificates for localhost_, 21 Dec 2017. [Online]. Available: https://letsencrypt.org/docs/certificates-for-localhost/.

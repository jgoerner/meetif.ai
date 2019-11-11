# Meetif.ai 
> Knowledge Graph to break the Ice ❄️

# What is __Meetif.ai__ about?
This repository contains a social knowledge graph (_meetif.ai_) for educational purposes. 
It fetches data from the social event platform Meetup.com and creates a graph containing events, groups, members, etc.
This graph is utilized to generate ["icebreaker"](https://www.urbandictionary.com/define.php?term=Ice%20Breaker)between people ([available ones](#what-icebreaker-are-available)). 
It is noteworthy that the actual raw data is **not contained** in this repository.
Please see [meetup.com/meetup_api](https://www.meetup.com/meetup_api/) for more information how to communicate with the Meetup.com API.

![](./docs/readme_0.png)

# What "Icebreaker" are available?

## Icebreaker 1 - Common Events 🎫
> The first icebreaker suggests events that both persons have been participated in.

![](./docs/readme_1.png)

[What is happening in the background?](#icebreaker-i)

## Icebreaker 2 - Cultural Differences 👽
> If there is no common event, there might be cultural differences that are interesting to talk about.

![](./docs/readme_2.png)

[What is happening in the background?](#icebreaker-ii)

## Icebreaker 3 - friend of a friend of a friend ... 👫👫👫
> Yet another option is to check, if you participated in an event with somebody who participated in another event, where also another person participated, who participated in another event, ..., until you reach the other person.

![](./docs/readme_3.png)

[What is happening in the background?](#icebreaker-iii)

## Icebreaker 4 - Facts! 🧠
If none of the aforementioned icebreakers can be applied, there are only facts left.
Those include statements like `1000000000000 is the number of bacteria on the surface of the human body`
or 
`250 is the number of Pokémon originally available in Pokémon Gold and Silver before Celebi was added`.

![](./docs/readme_4.png)

[What is happening in the background?](#icebreaker-iv)

# Technical Background

## How to start Meetif.ai?
In order to start the Knowledge Graph run the following command:
`docker-compose -f "docker-compose.yml" up -d --build`

This will start the containerized structure of Meetif.ai which is depicted in the following figure.

![](./docs/architecture.png)

## Icebreker - behind the scenes

### Icebreaker I
![](.docs/readme_1_bg.png)
1. an HTTP request is sent to the backend
2. a SPARQL query is raised against the RDF server 
3. the result of the SPARQL query is returned to the backend ...
4. ... and to the frontend

### Icebreaker II
![](.docs/readme_2_bg.png)
1. an HTTP request is sent to the backend
2. a SPARQL query is raised against the RDF server which is federated to [DBpedia](https://wiki.dbpedia.org/)
3. the result of the SPARQL query is returned to the backend ...
4. ... and to the frontend

### Icebreaker III
![](.docs/readme_3_bg.png)
1. an HTTP request is sent to the backend
2. a CYPHER query (including graph traversals) is raised against Neo4j
3. the result of the CYPHER query is returned to the backend ...
4. ... and to the frontend

### Icebreaker IV
![](.docs/readme_4_bg.png)
1. an HTTP request is sent to [Numbers API](http://numbersapi.com/)
3. the result of the CYPHER query is returned to the frontend

### Knowledge Graphs

### Frameworks

| Frameworks | Purpose | Website |
| --- | --- | --- |
| RDF4J | store RDF and handle RDF I/O | [https://rdf4j.org/](https://rdf4j.org/) |
| Neo4j | enabling graph traversals | [https://neo4j.com/](https://neo4j.com/) |
| Spring Boot | Backend| [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| React | Frontend | [https://reactjs.org/](https://reactjs.org/) |

### Infrastructure
| Tool | Purpose | Website |
| --- | --- | --- |
| Gradle | Build Tool | [https://gradle.org/](https://gradle.org/) |
| Docker | Handling Infrastructure Components| [https://www.docker.com/](https://www.docker.com/) |

### (Programming) Languages

| Language | Purpose | Website |
| --- | --- | --- |
| Kotlin | Backend | [https://kotlinlang.org/](https://kotlinlang.org/) |
| Typescript | Frontend | [https://www.typescriptlang.org/](https://www.typescriptlang.org/) |
| RML | JSON to RDF transformation | [http://rml.io/](http://rml.io/) |
| SPARQL | RDF Querying | [https://www.w3.org/TR/sparql11-query/](https://www.w3.org/TR/sparql11-query/) |
| CYPHER | Neo4j Querying | [https://neo4j.com/developer/cypher-query-language/](https://neo4j.com/developer/cypher-query-language/) |

### Utilized Vocabularies

| Ontology | Mainly used for | Website |
| --- | --- | --- |
| foaf |  Persons | [http://xmlns.com/foaf/spec/](http://xmlns.com/foaf/spec/) |
| schema | Events | [http://schema.org/](http://schema.org/) |
| dbr | DBPedia Resources | --- |


# How to contribute
Yes, there are lot of options to further improve this repository.
As it's main purpose is to show the possible combination of building blocks,
general best practices of software engineering (e.g. TDD) have been neglected.

You are more than happy to raise issues, refactor, open pull requests and improve this repository 🙂

# Meetif.ai 
> Knowledge Graph to break the Ice ❄️

# What it is about
This repository contains a social knowledge graph for educational purposes. 

![](./docs/readme_0.png)

### Icebreaker 1 - Common Events
The first - and easiest situation - would be, if the two persons share common events, both have been participated in.

##### How does it look like?
![](./docs/readme_1.png)

##### What is happening in the background?
![](.docs/readme_1_bg.png)
A SPARQL query is evaluated if both persons are linked to a common event.

### Icebreaker 2 - Cultural Differences
If there is no common event, there might be cultural differences that are interesting to talk about.

##### How does it look like?
![](./docs/readme_2.png)

##### What is happening in the background?
![](.docs/readme_2_bg.png)
The only location in the graph is the city.
Therefore SPARQL query sent against the `dpbedia.org/sparql` endpoint to fetch and compare the countries of those cities.

### Icebreaker 3 - friend of a friend of a friend ...
Yet another option is to check, if you participated in an event with somebody who participated in another event, 
where also another person participated, who participated in another event, ..., until you reach the other person.

##### How does it look like?
![](./docs/readme_3.png)

##### What is happening in the background?
![](.docs/readme_3_bg.png)
During initial loading some information from the RDF graph are mirrored into a Neo4j database.
Opposed to RDF-based solution, Neo4j allows for more advanced graph traversals.
The builtin `allShortestPath` CYPHER function is used to find the paths between the persons.
If those paths exist, a random one is chosen and returned.s

### Icebreaker 4 - Facts!
If none of the aforementioned icebreakers can be applied, there are only facts left.

##### How does it look like?
![](./docs/readme_4.png)

##### What is happening in the background?
![](.docs/readme_4_bg.png)
The [Numbers API](http://numbersapi.com/) gets fetched randomly to provide mind blowing trivia like
`1000000000000 is the number of bacteria on the surface of the human body`
or 
`250 is the number of Pokémon originally available in Pokémon Gold and Silver before Celebi was added`.




# Run the Knowledge Graph

### Prerequisites 
- Docker
- JDK
- Kotlin
- Node
- yarn

### How to start
In order to start the Knowledge Graph run the following commands in the given order from the root directory of this repository:
1. Start RDF server & Neo4j `docker-compose up -d`
2. Start the backend `./gradlew bootRun`
3. Start the frontend `yarn --cwd ./frontend start`

# Background Information

### Knowledge Graphs

### Frameworks

| Frameworks | Purpose | Website |
| --- | --- | --- |
| RDF4J | store RDF and handle RDF I/O | --- |
| Neo4j | enabling graph traversals | --- |
| Spring Boot | Backend| --- |
| React | Frontend | --- |

### Infrastructure
| Tool | Purpose | Website |
| --- | --- | --- |
| Gradle | Build Tool | --- |
| Docker | Handling Infrastructure Components| |

### (Programming) Languages

| Language | Purpose | Website |
| --- | --- | --- |
| Kotlin | Backend | |
| Typescript | Frontend | |
| RML | JSON to RDF transformation | --- |
| SPARQL | RDF Querying | --- |
| CYPHER | Neo4j Querying | --- |

### Utilized Vocabularies

| Ontology | Mainly used for | Website |
| --- | --- | --- |
| foaf |  Persons | |
| schema | Events | |
| dbr | DBPedia Resources | |



# How to contribute
Yes, there are lot of options to further improve this repository.
As it's main purpose is to show the possible combination of building blocks,
general best practices of software engineering (e.g. TDD) have been neglected.

You are more than happy to raise issues, refactor and open pull requests 🙂
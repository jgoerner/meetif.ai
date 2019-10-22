import React, { useState, useEffect } from "react";
import Styles from "./Main.module.css";
import PersonPicker from "../../organisms/PersonPicker/PersonPicker";
import { DummyDescription, DummyPerson, DummyPersons } from "../../misc/Constants";
import {IPersonCard} from "../../molecules/PersonCard/PersonCard";
import axios from "axios";

const Main = () => {

    const emptyPersons = new Map([[0, DummyPerson], [1, DummyPerson]]);
    const [persons, setPersons] = useState<Map<number, IPersonCard>>(emptyPersons);
    const [untouched, setUntouched] = useState(true);
    const [allPersons, setAllPersons] = useState<IPersonCard[]>([]);

    useEffect(() => {
        if (untouched) {
            setUntouched(false);
            axios.get("http://localhost:9090/api/persons")
                .then(r => setAllPersons([...r["data"]]))
                .catch(e => console.log(`Got the error ${e}`))
        }
    });

    const handleSubmit = () => {
        // convert state ids to form data
        const ids = Array.from(persons).map(([, person]) => person.id);
        const eventForm = new FormData();
        eventForm.append("p1", ids[0].toString());
        eventForm.append("p2", ids[1].toString() );

        axios.post("http://localhost:9090/api/icebreaker/commonEvents", eventForm)
            .then(r => {
                if (r["data"].length == 0) {
                    console.log("No common events...");
                    const countryForm = new FormData();
                    const cities = Array.from(persons).map(([, person]) => person.location);
                    countryForm.append("city1", cities[0]);
                    countryForm.append("city2", cities[1]);
                    axios.post("http://localhost:9090/api/icebreaker/differentCountry", countryForm)
                        .then(r => {
                            if (r["data"]) {
                                console.log(`Talk about the differences between ${cities[0]} and ${cities[1]}` );
                            } else {
                                console.log("Coming from same country...");
                                axios.get("http://numbersapi.com/random/")
                                    .then(r => console.log(r["data"]))
                            }
                        })
                        .catch(e => console.log("Error in second icebreaker - " + e))
                } else {
                    console.log("Start talking about events like " + JSON.stringify(r["data"]))
                }
            })
            .catch(e => console.log("Error in first icebreaker - " + e))
    };

    const handleReset = () => {
        setPersons(emptyPersons);
    };

    const handlePersonPick = (p: number, idx: number) => {
        const pCopy = new Map(persons);
        pCopy.set(idx, allPersons[p]);
        setPersons(pCopy);
    };

    const handleRandomPick = () => {
        const p1 = Math.floor(Math.random()*allPersons.length);
        const p2 = Math.floor(Math.random()*allPersons.length);
        const pCopy = new Map(persons);
        pCopy.set(0, allPersons[p1]);
        pCopy.set(1, allPersons[p2]);
        setPersons(pCopy);
    };

    let content = (
        <div className={Styles.Container}>
            <div />
            <div className={Styles.Content}>
                <h1>Here Comes the Heading</h1>
                <p>{DummyDescription}</p>
                <div className={Styles.PersonPicker}>
                    <PersonPicker
                        handlePersonPicked={(p: number) => handlePersonPick(p, 0)}
                        persons={allPersons}
                        picked={persons.get(0)}
                    />
                    <PersonPicker
                        handlePersonPicked={(p: number) => handlePersonPick(p, 1)}
                        persons={allPersons}
                        picked={persons.get(1)}
                    />
                </div>
                <button onClick={handleSubmit}>Go</button>
                <button onClick={handleReset}>Reset</button>
                <button onClick={handleRandomPick}>Random Pair</button>
            </div>
            <div />
        </div>
    );
    return content
};

export default Main;
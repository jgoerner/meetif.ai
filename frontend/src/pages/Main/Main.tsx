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
        const form = new FormData();
        form.append("p1", ids[0].toString());
        form.append("p2", ids[1].toString() );

        axios.post("http://localhost:9090/api/icebreaker/commonEvents", form)
            .then(r => console.log("All good\n" + JSON.stringify(r["data"])))
            .catch(e => console.log("Error: " + e))
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
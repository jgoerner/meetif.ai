import React from "react";
import Styles from "./Infobar.module.scss";
import Intention from "../../misc/Enums";

interface IProps {
    intention: Intention,
    children?: any
}

const Infobar = (props: IProps) => {
    let styling = [Styles.Infobar];

    switch (props.intention) {
        case Intention.GOOD:
            styling.push(Styles.GoodIntention);
            break;
        case Intention.BAD:
            styling.push(Styles.BadIntention);
            break;
        case Intention.NEUTRAL:
            styling.push(Styles.NeutralIntention);
            break;
        case Intention.PERSON:
            styling.push(Styles.PersonIntention);
            break;
        case Intention.EVENT:
            styling.push(Styles.EventIntention);
            break
    }

    return (
        <div className={styling.join(" ")}>
            {props.children}
        </div>
        )};

export default Infobar;
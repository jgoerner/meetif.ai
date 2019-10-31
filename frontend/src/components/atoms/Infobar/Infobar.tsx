import React from "react";
import Styles from "./Infobar.module.scss";
import Intention from "../../misc/Enums";

interface IProps {
    intention: Intention,
    children?: any
}

const Infobar = (props: IProps) => {
    let styling = [Styles.Infobar];

    if (props.intention == Intention.GOOD) {
        styling.push(Styles.GoodIntention);
    } else {
        styling.push(Styles.BadIntention);
    }

    return (
        <div className={styling.join(" ")}>
            {props.children}
        </div>
        )};

export default Infobar;
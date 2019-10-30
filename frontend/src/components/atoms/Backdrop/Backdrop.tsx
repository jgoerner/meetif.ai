import React from "react";
import Styles from "./Backdrop.module.scss";

interface IProps {
    visible: boolean,
    clickHandler: () => void
}

const Backdrop = (props: IProps) => {

    const styling = [Styles.Backdrop];
    if (props.visible) {
        styling.push(Styles.Visible);
    } else {
        styling.push(Styles.Hidden);
    }

    return <div className={styling.join(" ")} onClick={props.clickHandler} />
};

export default Backdrop;
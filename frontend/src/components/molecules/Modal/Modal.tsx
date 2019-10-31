import React from "react";
import Styles from "./Modal.module.scss";
import Backdrop from "../../atoms/Backdrop/Backdrop";

interface IProps {
    text: string,
    visible: boolean,
    clickHandler: () => void,
    children?: any
}

const Modal = (props: IProps) => {
    const styling = [Styles.Modal];
    if (props.visible) {
        styling.push(Styles.Visible)
    } else {
        styling.push(Styles.Hidden)
    }

    return (
        <React.Fragment>
            <Backdrop visible={props.visible} clickHandler={props.clickHandler}/>
            <div className={styling.join(" ")}>
                {props.children}
            </div>
        </React.Fragment>
    )};

export default Modal;
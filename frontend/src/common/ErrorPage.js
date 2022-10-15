import {Notification} from "./Notification";
import React from "react";
import {ERROR} from "./Utils";

export function ErrorPage({message, fields}) {
    return (
        <div className="background-container bg-image d-flex justify-content-center align-items-center">
            <div className="card col-sm-3 border-dark text-left">
                <Notification notification={{
                    show: true,
                    type: ERROR,
                    message: message,
                    fields: fields
                }}/>
            </div>
        </div>
    );
}

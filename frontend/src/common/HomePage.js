import React from 'react';
import {ADMIN_ROLE, CLIENT_ROLE, isAuthorized} from "../auth/Authentication";
import {AdminHomePage} from "../admin/AdminHomePage";
import {ClientHomePage} from "../client/ClientHomePage";
import {LoginPage} from "./LoginPage";

export default function HomePage() {
    return (
        isAuthorized(ADMIN_ROLE) ? <AdminHomePage/> :
            isAuthorized(CLIENT_ROLE) ? <ClientHomePage/> : <LoginPage/>
    );
}

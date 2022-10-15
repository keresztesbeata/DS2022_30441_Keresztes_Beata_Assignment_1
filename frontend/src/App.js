import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import './App.css';
import {LoginPage} from "./common/LoginPage";
import {RegisterPage} from "./common/RegisterPage";
import HomePage from "./common/HomePage";
import {ADMIN_ROLE, CLIENT_ROLE, isLoggedIn, ProtectedComponent} from "./auth/Authentication";
import {AdminHomePage} from "./admin/AdminHomePage";
import {ClientHomePage} from "./client/ClientHomePage";
import {AdminAccountsPage} from "./admin/AdminAccountsPage";

export default function App() {
    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<HomePage/>}/>
                    <Route path="/admin"
                           element={<ProtectedComponent component={<AdminHomePage/>} authority={ADMIN_ROLE}/>}/>
                    <Route path="/client"
                           element={<ProtectedComponent component={<ClientHomePage/>} authority={CLIENT_ROLE}/>}/>
                    <Route path="/login" element={isLoggedIn() ? <HomePage/> : <LoginPage/>}/>
                    <Route path="/register" element={<RegisterPage/>}/>
                    <Route path="/admin/accounts"
                           element={<ProtectedComponent component={<AdminAccountsPage/>} authority={ADMIN_ROLE}/>}/>
                </Routes>
            </BrowserRouter>
        </>
    );
}

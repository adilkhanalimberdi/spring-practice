import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import {HomePage} from "./pages/HomePage.tsx";
import {RegisterPage} from "./pages/RegisterPage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import {OAuth2RedirectHandler} from "./components/OAuth2RedirectHandler.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route
                  path="/"
                  element={
                      <ProtectedRoute>
                          <HomePage />
                      </ProtectedRoute>
                  }
              />

              <Route
                  path="/login"
                  element={
                      <LoginPage />
                  }
              />

              <Route
                  path="/register"
                  element={
                      <RegisterPage />
                  }
              />

              <Route
                  path="/oauth2/redirect"
                  element={
                      <OAuth2RedirectHandler />
                  }
              />

              <Route
                  path="*"
                  element={
                      <Navigate to="/" replace />
                  }
              />
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)

import api from "./api.ts";
import type {LoginRequest, RegisterRequest} from "../types";

export const AuthService = {
    async login(request: LoginRequest): Promise<void> {
        const response = await api.post("/auth/login", request);

        if (response.data && response.data.token) {
            localStorage.setItem("accessToken", response.data.token);
        }
    },

    async register(request: RegisterRequest): Promise<void> {
        const response = await api.post("/auth/register", request);

        if (response.data && response.data.token) {
            localStorage.setItem("accessToken", response.data.token);
        }
    }
}
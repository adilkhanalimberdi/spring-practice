import type {ProfileResponse} from "../types";
import api from "./api.ts";

export const UserService = {
    async getProfile(): Promise<ProfileResponse> {
        const response = await api.get("/users/profile");
        return response.data;
    }
}
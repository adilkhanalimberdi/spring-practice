import {useState} from "react";
import {useNavigate} from "react-router-dom";
import type {RegisterRequest} from "../types";
import {AuthService} from "../api/auth.service.ts";

export function RegisterPage() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const request: RegisterRequest = {
            username: username,
            email: email,
            password: password
        };

        try {
            await AuthService.register(request);
            navigate("/");
        } catch (err) {
            console.log(err);
            alert("Failed to sign up, please try again later");
        }
    }

    const handleSignInBtn = () => {
        navigate("/login");
    }

    const handleGoogle = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google?mode=register";
    }

    const handleGithub = () => {

    }

    return (
        <div className="h-screen flex justify-center items-center p-5 transition-all duration-200 bg-slate-200">
            <div className="max-w-xl w-full p-5 md:p-10 bg-white rounded-xl shadow-lg shadow-slate-300 flex flex-col gap-2">
                <div className="flex flex-col gap-10">
                    <div className="flex flex-col items-center justify-center gap-2">
                        <p className="text-3xl text-blue-700 font-semibold text-center">Create your account</p>
                        <p className="text-slate-700 text-center">Fill in this form to join us</p>
                    </div>

                    <div className="flex flex-col gap-1 items-center">
                        <div className="flex flex-row items-center justify-center gap-3 w-full">
                            <button id="sign-with-google"
                                    onClick={handleGoogle}
                                    className="flex-1 py-2 rounded-lg bg-slate-300">Google</button>
                            <button id="sign-with-github"
                                    onClick={handleGithub}
                                    className="flex-1 py-2 rounded-lg bg-black text-white">Github</button>
                        </div>
                    </div>
                </div>

                <div className="flex flex-row items-center justify-center gap-2">
                    <div className="flex-1 border-t border-slate-300"></div>
                    <p className="text-slate-500">Or</p>
                    <div className="flex-1 border-t border-slate-300"></div>
                </div>

                <form id="registerForm"
                      onSubmit={handleSubmit}
                      className="flex flex-col gap-5">
                    <div className="flex flex-col gap-1 group">
                        <label htmlFor="username" className="text-slate-400 group-has-hover:text-blue-600 group-has-focus:text-blue-700 transition-all duration-200">Username:</label>
                        <input type="text"
                               id="username"
                               placeholder="john.doe"
                               onChange={(e) => setUsername(e.target.value)}
                               className="px-5 py-3 rounded-lg border border-slate-400 focus:outline-none hover:ring-0 hover:border-blue-600 focus:border-blue-700  transition-all duration-200"
                               required/>
                    </div>

                    <div className="flex flex-col gap-1 group">
                        <label htmlFor="email" className="text-slate-400 group-has-hover:text-blue-600 group-has-focus:text-blue-700 transition-all duration-200">Email address:</label>
                        <input type="email"
                               id="email"
                               placeholder="john.doe@example.com"
                               onChange={(e) => setEmail(e.target.value)}
                               className="px-5 py-3 rounded-lg border border-slate-400 focus:outline-none hover:ring-0 hover:border-blue-600 focus:border-blue-700  transition-all duration-200"
                               required/>
                    </div>

                    <div className="flex flex-col gap-1 group">
                        <label htmlFor="password" className="text-slate-400 group-has-hover:text-blue-600 group-has-focus:text-blue-700 transition-all duration-200">Password:</label>
                        <input type="password"
                               id="password"
                               placeholder="******"
                               onChange={(e) => setPassword(e.target.value)}
                               className="px-5 py-3 rounded-lg border border-slate-400 focus:outline-none hover:ring-0 hover:border-blue-600 focus:border-blue-700  transition-all duration-200"
                               required/>
                        <p className="text-slate-400">Must be at least 8 characters.</p>
                    </div>

                    <div className="flex">
                        <button type="submit"
                                className="px-5 py-3 flex-1 text-white rounded-lg bg-blue-600 hover:bg-blue-700 active:bg-blue-800 transition-all duration-200">Sign Up</button>
                    </div>
                </form>

                <div className="flex items-center justify-center gap-2">
                    <p className="text-slate-600">Have an account?</p>
                    <button id="sign-in-btn"
                            onClick={handleSignInBtn}
                            className="text-blue-500 active:text-blue-700 cursor-pointer">Sign in</button>
                </div>
            </div>
        </div>
    )
}
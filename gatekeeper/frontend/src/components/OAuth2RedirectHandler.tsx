import {useNavigate, useSearchParams} from "react-router-dom";
import {useEffect} from "react";

export const OAuth2RedirectHandler = () => {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        const token = searchParams.get("token");

        if (token) {
            localStorage.setItem("accessToken", token);
            navigate("/");
        } else {
            navigate("/login");
        }
    }, [searchParams, navigate]);

    return (
        <div className="flex h-screen w-full items-center justify-center bg-slate-700 text-white">
            <p className="text-xl font-semibold animate-pulse">Authorizing via Google...</p>
        </div>
    );
}
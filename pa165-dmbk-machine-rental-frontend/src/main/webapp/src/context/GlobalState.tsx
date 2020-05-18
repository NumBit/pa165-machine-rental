import React, { createContext, useReducer } from "react";
import AppReducer from "./AppReducer";

type ContextProps = {
  signedIn: boolean;
  user: any;
  switchRole?: any;
};

const initialState = {
  signedIn: true,
  user: {
    type: "CustomerDto",
    id: 1,
    login: "testuser",
    legalForm: "INDIVIDUAL",
    email: "test@email.com",
    isAdmin: false,
  },
};

export const GlobalContext = createContext<ContextProps>(initialState);

export const GlobalProvider = ({ children }: any) => {
  const [state, dispatch] = useReducer(AppReducer, initialState);

  function switchRole(role: boolean) {
    dispatch({
      type: "SWITCH_ROLE",
      payload: role,
    });
  }

  return (
    <GlobalContext.Provider
      value={{
        switchRole,
        signedIn: state.signedIn,
        user: state.user,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};

import React, { createContext, useReducer } from "react";
import AppReducer from "./AppReducer";

export interface Customer {
  type: string,
  id: number,
  login: string,
  legalForm: string,
  email: string
}

export interface Admin {
  type: string,
  id: number,
  login: string,
  name: string,
  sureName: string
}

export interface Unauthenticated {
  objectName: string,
}

export type User = | Customer | Admin | Unauthenticated;

export function isAdmin(object: any): object is Admin {
  return 'type' in object && 'id' in object && 'login' in object
              && 'name' in object && 'sureName' in object;
}

export function isCustomer(object: any): object is Customer {
  return 'type' in object && 'id' in object && 'login' in object
              && 'legalform' in object && 'email' in object;
}

export function isUnauthenticated(object: any): object is Unauthenticated {
  return 'objectName' in object;
}

type ContextProps = {
  signedIn: boolean;
  user: User;
  switchRole?: any;
};

const initialState = {
  signedIn: true,
  user: {
    objectName: "Unauthenticated",
  },
};

export const GlobalContext = createContext<ContextProps>(initialState);

export const GlobalProvider = ({ children }: any) => {
  const [state, dispatch] = useReducer(AppReducer, initialState);

  function switchRole(role: User) {
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

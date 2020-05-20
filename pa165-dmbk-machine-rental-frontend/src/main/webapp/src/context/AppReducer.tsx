import { User } from "./GlobalState";

type Action = {
  type: any,
  payload: User
}

export default (state: any, action: Action) => {
  switch (action.type) {
    case "SWITCH_ROLE":
      return {
        ...state,
        user: action.payload,
      };
    default:
      return state;
  }
};

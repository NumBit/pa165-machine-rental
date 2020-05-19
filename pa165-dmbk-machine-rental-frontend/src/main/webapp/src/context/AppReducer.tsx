export default (state: any, action: any) => {
  switch (action.type) {
    case "SWITCH_ROLE":
      return {
        ...state,
        user: {
          type: "CustomerDto",
          id: 1,
          login: "testuser",
          legalForm: "INDIVIDUAL",
          email: "test@email.com",
          isAdmin: action.payload,
        },
      };
    default:
      return state;
  }
};

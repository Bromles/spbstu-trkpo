import { render } from "@testing-library/react";
import { Providers } from "./Providers";

describe("StoreProvider", () => {
  it("should render", () => {
    render(
      <Providers>
        <></>
      </Providers>
    );
  });
});

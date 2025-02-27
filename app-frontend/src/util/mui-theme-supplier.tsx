import * as React from 'react';
import { ThemeProvider } from '@emotion/react';
import { CssBaseline, createTheme } from '@mui/material';
import { teal } from '@mui/material/colors';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: teal,
  },
});

type Props = {
  children: React.ReactNode;
};

const MuiThemeSupplier: React.FC<Props> = ({
  children,
}): React.ReactElement => (
  <ThemeProvider theme={darkTheme}>
    <CssBaseline />
    {children}
  </ThemeProvider>
);

export { MuiThemeSupplier };

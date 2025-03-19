import * as React from 'react';
import { ThemeProvider } from '@emotion/react';
import { CssBaseline, GlobalStyles, createTheme } from '@mui/material';
import { blueGrey } from '@mui/material/colors';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: blueGrey,
  },
});

const globalStyles = {
  'body, #app-mount': {
    height: '100vh',
    margin: 0,
    padding: 0,
    display: 'flex',
    flexDirection: 'column',
    fontFamily: 'Roboto, sans-serif',
  },
};

type Props = {
  children: React.ReactNode;
};

const MuiThemeSupplier: React.FC<Props> = ({
  children,
}): React.ReactElement => (
  <ThemeProvider theme={darkTheme}>
    <CssBaseline />
    <GlobalStyles styles={globalStyles} />
    {children}
  </ThemeProvider>
);

export { MuiThemeSupplier };

const oidcProperties = {
  baseURL: 'http://localhost:3000',
  serverBaseURL: 'http://localhost:8080',
  clientId: 'master-client',
  redirectUri: '/login/oauth2/code',

  // response
  singingErrorResponse: 'access_denied'
}

export default oidcProperties
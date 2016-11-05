import { MonitorDashboardPage } from './app.po';

describe('monitor-dashboard App', function() {
  let page: MonitorDashboardPage;

  beforeEach(() => {
    page = new MonitorDashboardPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});

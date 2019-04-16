using Microsoft.Extensions.Hosting;
using System.Diagnostics;
using System.Threading;
using System.Threading.Tasks;
using TemperatureAPI_Test.DataSourceService;

namespace TemperatureAPI_Test.BackgroundServices
{
    public class BService_AlternateTemp : BackgroundService
    {        
        private readonly IDataSource _iDataSource;

        public BService_AlternateTemp(IDataSource iDataSource)
        {
            _iDataSource = iDataSource;
            _iDataSource.InitDataSource();
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            Debug.WriteLine("Background service started!");

            await Task.Delay(30000);

            while (!stoppingToken.IsCancellationRequested)
            {                
                Debug.WriteLine("Switching temperature json");
                await _iDataSource.DoWorkAsync();
                await Task.Delay(30000, stoppingToken);
            }
        }       
    }
}

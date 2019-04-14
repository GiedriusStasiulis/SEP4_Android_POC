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

            await Task.Delay(10000);

            while (!stoppingToken.IsCancellationRequested)
            {                
                Debug.WriteLine("Switching temperature source");
                await _iDataSource.DoWorkAsync();
                await Task.Delay(10000, stoppingToken);
            }
        }       
    }
}

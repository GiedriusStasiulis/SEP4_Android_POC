using System;
using System.Threading.Tasks;

namespace TemperatureAPI_Test.DataSourceService
{
    public class DataSource : IDataSource
    {
        private readonly string TempSourceOne = "{\"temperature\":\"25.6\",\"timestamp\":\"2019-04-14T12:00:00\"}";
        private readonly string TempSourceTwo = "{\"temperature\":\"28.2\",\"timestamp\":\"2019-04-14T12:00:30\"}";

        private string DataSourceStr;

        public void InitDataSource()
        {
            DataSourceStr = TempSourceOne;
        }

        public async Task DoWorkAsync()
        {
            DataSourceStr = GetData();

            if (DataSourceStr == TempSourceOne)
            {
                DataSourceStr = TempSourceTwo;
            }

            else if (DataSourceStr == TempSourceTwo)
            {
                DataSourceStr = TempSourceOne;
            }

            await Task.CompletedTask;
        }

        public string GetData()
        {
            return DataSourceStr;
        }
    }
}

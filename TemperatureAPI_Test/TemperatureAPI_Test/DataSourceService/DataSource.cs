using System.Threading.Tasks;

namespace TemperatureAPI_Test.DataSourceService
{
    public class DataSource : IDataSource
    {
        private readonly string TempSourceOne = "{\"temperature\":\"25.6\",\"timestamp\":\"2019-04-14T12:00:00\"}";
        private readonly string TempSourceTwo = "{\"temperature\":\"28.2\",\"timestamp\":\"2019-04-14T12:00:30\"}";

        private string JsonStr;

        public void InitDataSource()
        {
            JsonStr = TempSourceOne;
        }

        public async Task DoWorkAsync()
        {
            JsonStr = GetJson();

            if (JsonStr == TempSourceOne)
            {
                JsonStr = TempSourceTwo;
            }

            else if (JsonStr == TempSourceTwo)
            {
                JsonStr = TempSourceOne;
            }

            await Task.CompletedTask;
        }

        public string GetJson()
        {
            return JsonStr;
        }
    }
}

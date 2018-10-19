public class Trip
{
    String transportType;
    int distance;
    Time duration;

    public Trip(String inTransport, int inDistance, Time inDuration)
    {
        this.setTransportType(inTransport);
        this.setDistance(inDistance);
        this.setDuration(inDuration);
    }

    public void setTransportType(String inTransport)
    {
        transportType = inTransport;
    }

    public void setDistance(int inDistance)
    {
        distance = inDistance;
    }

    public void setDuration(Time inDuration)
    {
        duration = inDuration;
    }

    public String getTransportType() { return transportType; }
    public int getDistance() { return distance; }
    public Time getDuration() { return duration; }
}

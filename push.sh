COMPONENT=jmeter
VERSION=5.4

IMAGE=patrocinio/$COMPONENT:$VERSION

echo Pushing component $COMPONENT as version $VERSION
docker tag $IMAGE $IMAGE
docker push $IMAGE
